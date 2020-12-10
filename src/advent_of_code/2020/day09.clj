(ns advent-of-code.2020.day09
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn parse-input [input]
  (->> (str/split input #"\n")
       (mapv #(Long/parseLong %))))

(def input
  (->> "resources/2020/day09.txt"
       (slurp)
       (parse-input)))

(def test1 "35\n20\n15\n25\n47\n40\n62\n55\n65\n95\n102\n117\n150\n182\n127\n219\n299\n277\n309\n576")
(def input1 (parse-input test1))

(defn part1 [data preamble-size]
  (-> (for [nums (partition (inc preamble-size) 1 data)
            :let [pair-sums (->> (combo/combinations (take preamble-size nums) 2)
                                 (map #(apply + %))
                                 (set))]
            :when (not (contains? pair-sums (last nums)))]
        (last nums))
      (first)))

;(part1 input 25)
;=> 258585477

(defn get-nums [nums target-num]
  (reduce (fn [ns n]
            (let [res (conj ns n)]
              (if (<= target-num (apply + res))
                (reduced res)
                res)))
          (take 1 nums)
          (drop 1 nums)))

(defn part2 [data preamble-size]
  (let [res (let [val (part1 data preamble-size)]
               (loop [nums data]
                 (let [nums' (get-nums nums val)]
                   (if (= (apply + nums') val)
                     nums'
                     (recur (rest nums))))))]
    (+ (apply min res) (apply max res))))

;(part2 input 25)
;=> 36981213
