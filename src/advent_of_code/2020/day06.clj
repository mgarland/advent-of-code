(ns advent-of-code.2020.day06
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-line [line]
  (->> (str/split line #"\n")
       (mapv set)))

(defn parse-input [input]
  (->> (str/split input #"\n\n")
       (mapv parse-line)))

(def input
  (->> "resources/2020/day06.txt"
       (slurp)
       (parse-input)))

(def test1 "abc\n\na\nb\nc\n\nab\nac\n\na\na\na\na\n\nb\n")
(def input1 (parse-input test1))

(defn part1 [forms]
  (->> forms
       (map #(apply set/union %))
       (map count)
       (reduce +)))

(defn part2 [forms]
  (->> forms
       (map #(apply set/intersection %))
       (map count)
       (reduce +)))
