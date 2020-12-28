(ns advent-of-code.2020.day22
  (:require [clojure.string :as str]))

(defn parse-player-input [player-input]
  (->> (str/split player-input #"\n")
       (rest)
       (map #(Long/parseLong %))))

(defn parse-input [input]
  (->> (str/split input #"\n\n")
       (map parse-player-input)))

(def input (slurp "resources/2020/day22.txt"))

(def input1 "Player 1:\n9\n2\n6\n3\n1\n\nPlayer 2:\n5\n8\n4\n7\n10")

(defn play [cards1 cards2]
  (cond
    (empty? cards1)
    cards2

    (empty? cards2)
    cards1

    :else
    (let [p1 (first cards1)
          p2 (first cards2)]
      (if (< p2 p1)
        (recur (concat (rest cards1) [p1 p2]) (rest cards2))
        (recur (rest cards1) (concat (rest cards2) [p2 p1]))))))

(defn part1 [cards]
  (let [win-hand (play (first cards) (second cards))
        pts (reverse (range 1 (inc (count win-hand))))]
    (->> (map #(* %1 %2) win-hand pts)
         (apply +))))
;;35299

(defn part2 []
  )
