(ns advent-of-code.2020.day05
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-input [input]
  (->> input
       (str/split-lines)))

(def input
  (->> "resources/2020/day05.txt"
       (slurp)
       (parse-input)))

(def test1 "FBFBBFFRLR\nBFFFBBFRRR\nFFFBBBFRRR\nBBFFBBFRLL\n")
(def input1 (parse-input test1))

(defn find-pos [nums steps]
  (if (= 1 (count nums))
    (first nums)
    (if (#{\F \L} (first steps))
      (recur (take (/ (count nums) 2) nums) (rest steps))
      (recur (drop (/ (count nums) 2) nums) (rest steps)))))

(defn find-seat-id [rows cols pass]
  (let [row (find-pos rows (take 7 pass))
        col (find-pos cols (drop 7 pass))]
    (+ col (* row 8))))

(defn taken-seat-ids [rows cols passes]
  (->> passes
       (map #(find-seat-id (range rows) (range cols) %))))

(defn part1 [passes]
  (->> (taken-seat-ids 128 8 passes)
       (apply max)))

(defn part2 [passes]
  (let [taken-seats (-> (taken-seat-ids 128 8 passes)
                        (sort))
        all-seats (-> (range (first taken-seats) (last taken-seats))
                      (set))]
    (-> (set/difference all-seats (set taken-seats))
        (first))))
