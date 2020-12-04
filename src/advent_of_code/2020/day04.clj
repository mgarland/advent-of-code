(ns advent-of-code.2020.day04
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-line [line]
  (->> (str/split line #"\s+")
       (map #(str/split % #":"))
       (map (fn [[k v]] [(keyword k) v]))
       (into {})))

(defn parse-input [input]
  (->> (str/split input #"\n\n")
       (map parse-line)))

(def input
  (->> "resources/2020/day04.txt"
       (slurp)
       (parse-input)))

(def test1 "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm\n\niyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\nhcl:#cfa07d byr:1929\n\nhcl:#ae17e1 iyr:2013\neyr:2024\necl:brn pid:760753108 byr:1931\nhgt:179cm\n\nhcl:#cfa07d eyr:2025 pid:166559648\niyr:2011 ecl:brn hgt:59in")
(def input1 (parse-input test1))

(def passport-fields
  {:byr {:valid-fn (fn [val]
                     (<= 1920 (Integer/parseInt val) 2002))}
   :iyr {:valid-fn (fn [val]
                     (<= 2010 (Integer/parseInt val) 2020))}
   :eyr {:valid-fn (fn [val]
                     (<= 2020 (Integer/parseInt val) 2030))}
   :hgt {:valid-fn (fn [val]
                     (let [[_ num unit] (re-find #"(\d+)(cm|in)" val)]
                       (cond
                         (= "in" unit) (<= 59 (Integer/parseInt num) 76)
                         (= "cm" unit) (<= 150 (Integer/parseInt num) 193)
                         :default false)))}
   :hcl {:valid-fn (fn [val]
                     (-> (re-find #"^#[0-9a-f]{6}$" val)
                         (boolean)))}
   :ecl {:valid-fn (fn [val]
                     (-> (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} val)
                         (boolean)))}
   :pid {:valid-fn (fn [val]
                     (-> (re-find #"^\d{9}$" val)
                         (boolean)))}
   :cid {:valid-fn (fn [_val] true) :optional true}})

(def required-fields
  (->> passport-fields
       (filter (fn [[_k v]] (not (:optional v))))
       (keys)
       (set)))

(defn has-required-fields? [passport]
  (-> (set/difference required-fields (set (keys passport)))
      (count)
      (zero?)))

(defn valid-field? [[field value]]
  (let [vfn (-> passport-fields
                (get field)
                (:valid-fn))]
    (vfn value)))

(defn has-valid-fields? [passport]
  (->> (map valid-field? passport)
       ((complement some) false?)))

(defn valid? [passport]
  (and (has-required-fields? passport)
       (has-valid-fields? passport)))

(defn part1 []
  (->> input
       (filter has-required-fields?)
       (count)))

(defn part2 []
  (->> input
       (filter valid?)
       (count)))
