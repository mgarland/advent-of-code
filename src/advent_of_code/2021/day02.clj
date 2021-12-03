(ns advent-of-code.2021.day02
  (:require [clojure.string :as str]
            [clojure.set :as set]))


(defn parse-line [line]
  (let [[op arg] (str/split line #"\s+")]
    [op (Long/parseLong arg)]))

(defn parse-input [input]
  (->> (str/split input #"\n")
       (mapv parse-line)))

(def input
  (->> "resources/2021/day02.txt"
       slurp
       parse-input))

(def example "forward 5\ndown 5\nforward 8\nup 3\ndown 8\nforward 2")

(defn move1
  "Returns the new position after applying the action
  position: {:x val :y val}
  action: [direction amount], i.e. [\"forward\" 5]."
  [position [direction amount]]
  (case direction
    "forward" (update position :x + amount)
    "down" (update position :y - amount)
    "up" (update position :y + amount)))

(defn move2
  "Returns the new position after applying the action
  position: {:x val :y val :aim 0}
  action: [direction amount], i.e. [\"forward\" 5]."
  [{:keys [aim] :as position} [direction amount]]
  (case direction
    "forward" (-> position
                  (update :x + amount)
                  (update :y + (* aim amount)))
    "down" (update position :aim + amount)
    "up" (update position :aim - amount)))

(comment
  ;; part 1
  (->> input
       (reduce move1 {:x 0 :y 0})
       vals
       (apply *))

  ;; part 2
  (->> input
       (reduce move2 {:x 0 :y 0 :aim 0})
       ((fn [{:keys [x y]}]
          (* x y))))
  )
