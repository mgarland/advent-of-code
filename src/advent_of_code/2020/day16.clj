(ns advent-of-code.2020.day16
  (:require [clojure.string :as str])
  (:import (java.util BitSet)))


(def input (slurp "resources/2020/day16.txt"))
(def input1 "class: 1-3 or 5-7\nrow: 6-11 or 33-44\nseat: 13-40 or 45-50\n\nyour ticket:\n7,1,14\n\nnearby tickets:\n7,3,47\n40,4,50\n55,2,20\n38,6,12")

(defn parse-valid-lines-to-bit-set [lines]
  (let [bit-set (new BitSet)]
    (doseq [line lines
            :let [[_ _ low1 high1 low2 high2]
                  (re-find #"^(.*): (\d+)-(\d+) or (\d+)-(\d+)$" line)]]
      (doto bit-set
        (.set (Long/parseLong low1) (inc (Long/parseLong high1)))
        (.set (Long/parseLong low2) (inc (Long/parseLong high2)))))
    bit-set))

(defn part1 [input]
  (let [input' (str/split input #"\n\n")
        valid-fields (-> input'
                         (first)
                         (str/split #"\n")
                         (parse-valid-lines-to-bit-set))
        nearby-fields (as-> input' $
                            (nth $ 2)
                            (str/split $ #"\n")
                            (rest $)
                            (mapcat #(str/split % #",") $)
                            (map #(Long/parseLong %) $))]
    (->> nearby-fields
         (remove #(.get valid-fields %))
         (apply +))))
;;25059

(defn part2 []
  )
