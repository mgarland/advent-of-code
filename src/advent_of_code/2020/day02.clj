(ns advent-of-code.2020.day02
  (:require [clojure.string :as str]))

(defn update-elements [line]
  (-> line
      (update 0 #(Integer/parseInt %))
      (update 1 #(Integer/parseInt %))
      (update 2 first)))

(defn parse-line [line]
  (->> line
       (re-matcher #"^(\d+)-(\d+)\s+(\S):\s+(\S+)$")
       (re-find)
       (rest)
       (vec)
       (update-elements)))

(def parsed-input
  (->> "resources/2020/day02.txt"
       (slurp)
       (str/split-lines)
       (map parse-line)))

(def test1 "1-3 a: abcde\n1-3 b: cdefg\n2-9 c: ccccccccc")
(def parsed-test1
  (->> test1
       (str/split-lines)
       (map parse-line)))

(defn part1 [parsed-input]
  (apply +
         (for [[low high ch pwd] parsed-input
               :let [cnt (get (frequencies pwd) ch 0)]
               :when (<= low cnt high)]
           1)))

(defn part2 [parsed-input]
  (apply +
         (for [[pos1 pos2 ch pwd] parsed-input
               :when (not= (= ch (get pwd (dec pos1) nil))
                           (= ch (get pwd (dec pos2) nil)))]
           1)))
