(ns advent-of-code.2020.day03
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (-> (map #(if (= \# %) 1 0) line)
      (vec)))

(def input
  (->> "resources/2020/day03.txt"
       (slurp)
       (str/split-lines)
       (map parse-line)
       (vec)))

(def map1 "..##.......\n#...#...#..\n.#....#..#.\n..#.#...#.#\n.#...##..#.\n..#.##.....\n.#.#.#....#\n.#........#\n#.##...#...\n#...##....#\n.#..#...#.#")
(def input-map1
  (->> test1
       (str/split-lines)
       (map parse-line)
       (vec)))

(defn tree-map [tm slope-x slope-y]
  "`tm` is the tree map; tree positions have a value of 1
   `slope-x` x distance for slope
   `slope-y` y distance for slope"
  (letfn [(do-it [cur-x cur-y acc]
            (let [next-x (mod (+ cur-x slope-x) (count (first tm)))
                  next-y (+ cur-y slope-y)
                  acc' (+ acc (get-in tm [cur-y cur-x]))]
              (if (<= (count tm) next-y)
                acc'
                (recur next-x next-y acc'))))]
    (do-it 0 0 0)))

(defn part1 []
  (tree-map input 3 1))

(defn part2 []
  (* (tree-map input 1 1)
     (tree-map input 3 1)
     (tree-map input 5 1)
     (tree-map input 7 1)
     (tree-map input 1 2)))
