(ns advent-of-code.2020.day01
  (:require [clojure.math.combinatorics :as combo]))

(def entries [1721 979 366 299 675 1456])

(defn split-nums [nums-str]
  (.split nums-str "\\s+"))

(def input
  (->> "resources/2020/day01.txt"
       (slurp)
       (split-nums)
       (map #(Long/parseLong %))))

(defn make-groups [group-size entries]
  (combo/combinations entries group-size))

(defn find-group [total groups]
  (->> groups
       (filter (fn [[& entries]]
                 (= total (apply + entries))))
       first))

(defn do-it [entries total group-size]
  (->> entries
       (make-groups group-size)
       (find-group total)
       (apply *)))

(comment
  ;; part 1
  (do-it input 2020 2)

  ;; part 2
  (do-it input 2020 3))

