(ns advent-of-code.2020.day07
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn contents [contents-str]
  (if (= "no other bags." contents-str)
    []
    (let [matcher (re-matcher #"(\d+) (\S+\s+\S+) (?:bag|bags)[,.]" contents-str)]
      (loop [m matcher
             contents []]
        (let [[_ cnt' color'] (re-find m)]
          (if color'
            (recur m (conj contents {color' (Integer/parseInt cnt')}))
            contents))))))

(defn parse-line [line]
  (let [[_ color contents-str] (re-find #"^(\S+\s+\S+) bags contain (.*)$" line)]
    [color (contents contents-str)]))

(defn parse-input [input]
  (->> (str/split input #"\n")
       (map parse-line)
       (into {})))

(def input
  (->> "resources/2020/day07.txt"
       (slurp)
       (parse-input)))

(def test1 "light red bags contain 1 bright white bag, 2 muted yellow bags.\ndark orange bags contain 3 bright white bags, 4 muted yellow bags.\nbright white bags contain 1 shiny gold bag.\nmuted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\nshiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\ndark olive bags contain 3 faded blue bags, 4 dotted black bags.\nvibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\nfaded blue bags contain no other bags.\ndotted black bags contain no other bags.")
(def input1 (parse-input test1))

(def rules1
  (into {} (map (fn [[k v]]
                  [k (vec (mapcat keys v))])
                input)))

(defn part1 [color rules]
  (->> (for [rule rules]
         (loop [colors (val rule)]
           (if (empty? colors)
             nil
             (if (contains? (set colors) color)
               (key rule)
               (let [colors' (get rules (first colors))]
                 (recur (apply conj (rest colors) colors')))))))
       (remove nil?)
       (count)))

