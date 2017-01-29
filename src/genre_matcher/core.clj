(ns genre-matcher.core
  (:require [clojure.java.io :as io]
            [clojure.string :as st]
            [clojure.tools.cli :refer [parse-opts]]
            [cheshire.core :refer :all])
  (:gen-class))

(def non-word-regex
  "regular expression for finding all non-word characters and leaving spaces for delimiting"
  #"(?![a-zA-Z0-9À-ÿ\s])(\W)")

(defn clean-string [s]
  "remove non-word characters, replace with '' to lowercase"
  (-> s
    (st/replace non-word-regex " ")
    (st/replace #"\s" " ")
    st/lower-case))

(defn parse-words [s]
  "delimit string by space (' ') and remove '' characters"
  (remove #{""} (st/split s #" ")))

(defn get-title [l]
  "return the title of a book"
  (second (first l)))

(defn get-description [l]
  "return the description of a book"
  (second (second l)))

(defn get-words-list [s]
  "parse the book json and return a map for each book"
  (let [title (get-title s)
        description (parse-words (clean-string (get-description s)))]
    {:title title
     :description description}))

(defn split-line [l]
  "split each line by ','"
  (st/split l #","))

(defn read-csv [f]
  "return list from splitting csv by line"
  (let [csv (st/lower-case (st/replace f #"\r" ""))
        lines (rest (st/split csv #"\n"))]
    (map split-line lines)))

(defn map-csv [c]
  "parse the csv and convert it to a map for easier (and constant) look-up"
  {:genre (nth c 0)
   :word (nth c 1)
   :score (Integer/parseInt (nth c 2))})

(defn gen-map [m l]
  "loop through all of the books, for each one, check to see if the description contains a keyword, return the map with genre"
  (loop [ul (empty '())
         fl (first l)
         rl (rest l)]
    (if fl
      (recur (conj ul
                   (loop [f (first m)
                          r (rest m)
                          u {}]
                     (if (some #{(:word f)} (:description fl))
                       (merge-with + u {:title (:title fl), (keyword (:genre f)), (:score f)})
                       (recur (first r) (rest r) u))))
             (first rl)
             (rest rl)) ul)))

(def cli-options
  [["-b" "--books BOOKS" "* [Required] File containing the JSON with book titles and descriptions."]
   ["-k" "--keywords KEYWORDS" "* [Required] File containing the genres and keywords."]
   ["-h" "--help" "Print this help menu"
    :default false]])

(def required-opts #{:books :keywords})

(defn missing-required? [opts]
  "Returns true if opts is missing any of the required-opts"
  (not-every? opts required-opts))

(defn -main [& args]
  "Match books with their correct genre based on keywords"
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (when (or (:help options)
              (missing-required? options))
      (println summary))
    (if (and
      (and (not (nil? (:books options))) (.exists (io/file (:books options))))
      (and (not (nil? (:keywords options))) (.exists (io/file (:keywords options)))))
      (let [books (parse-stream (io/reader (:books options)))
            book-words (doall (map get-words-list books))
            keyword-map (doall (map map-csv (read-csv (slurp (:keywords options)))))]
    (gen-map keyword-map book-words))
      (println "\nCan't seem to locate those files! Adding the absolute path might help!"))))
