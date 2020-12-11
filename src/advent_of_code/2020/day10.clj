(ns advent-of-code.2020.day10
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn parse-input [input]
  (->> (str/split input #"\n")
       (mapv #(Long/parseLong %))))

(def raw-input (slurp "resources/2020/day10.txt"))
(def input (parse-input raw-input))

(def raw-input1 "16\n10\n15\n5\n1\n11\n7\n19\n6\n12\n4")
(def input1 (parse-input raw-input1))
(def raw-input2 "28\n33\n18\n42\n31\n14\n46\n20\n48\n47\n24\n23\n49\n45\n19\n38\n39\n11\n1\n32\n25\n35\n8\n17\n7\n9\n4\n2\n34\n10\n3")
(def input2 (parse-input raw-input2))

(defn joltage-diffs [joltage-ratings]
  (let [max-joltage (+ 3 (apply max joltage-ratings))]
    (->> joltage-ratings
         (cons 0)
         (cons max-joltage)
         (sort)
         (partition 2 1)
         (group-by (fn [[a b]] (- b a))))))

(defn part1 [joltage-ratings]
  (let [diffs (joltage-diffs joltage-ratings)]
    (* (count (get diffs 1 0)) (count (get diffs 3 0)))))

(defn part2 [joltage-ratings]
  )

