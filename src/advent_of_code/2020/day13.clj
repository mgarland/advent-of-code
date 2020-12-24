(ns advent-of-code.2020.day13
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (->> (str/split line #",")
       (remove #(= "x" %))
       (mapv #(Long/parseLong %))))

(defn parse-input [input]
  (let [lines (str/split input #"\n")]
    [(Long/parseLong (first lines))
     (parse-line (second lines))]))

(def raw-input (slurp "resources/2020/day13.txt"))
(def input (parse-input raw-input))

(def raw-input1 "939\n7,13,x,x,59,x,31,19")
(def input1 (parse-input raw-input1))

(defn wait-time [start bus-num]
  (- (+ bus-num (* (int (/ start bus-num)) bus-num)) start))

(defn part1 [input]
  (let [start (first input)]
    (->> (reduce (fn [[low-wait-time _bus-num :as low] bus-num]
                   (let [cur-wait-time (wait-time start bus-num)]
                     (if (< cur-wait-time low-wait-time)
                       [cur-wait-time bus-num]
                       low)))
                [Long/MAX_VALUE 0]
                (second input))
         (apply *))))
;;3966

(defn part2 []
  )
