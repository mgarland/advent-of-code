(ns advent-of-code.2020.day14
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(defn parse-line [line]
  (let [[_ op val] (re-find #"^(.*) = (.*)$" line)]
    [op val]))

(defn parse-input [input]
  (->> (str/split input #"\n")
       (mapv parse-line)))

(def raw-input (slurp "resources/2020/day14.txt"))
(def program (parse-input raw-input))

(def raw-input1 "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\nmem[8] = 11\nmem[7] = 101\nmem[8] = 0")
(def program1 (parse-input raw-input1))

(defn apply-mask [mask val]
  (map (fn [m v] (if (= \X m) v m)) mask val))

(defn bit-seq->num [bit-seq]
  (as-> bit-seq $
        (apply str $)
        (Long/parseLong $ 2)))

(defn num->bit-seq [num]
  (->> (-> num
           (Long/toBinaryString)
           (reverse)
           (concat (repeat \0)))
       (take 36)
       (reverse)))

(defn exec1 [state [op val :as _instruction]]
  (if (= "mask" op)
    (assoc state :mask (seq val))
    (let [[_ pos] (re-find #"mem\[(.*)\]" op)]
      (assoc-in state
                [:mem (Long/parseLong pos)]
                (apply-mask (:mask state) (num->bit-seq (Long/parseLong val)))))))

(defn part1 [program]
  (->> (reduce exec1
               {:mask (repeat 36 0)
                :mem  {}}
               program)
       :mem
       (map (fn [[_ bit-seq]] (bit-seq->num bit-seq)))
       (apply +)))
;;3059488894985

(def raw-input2 "mask = 000000000000000000000000000000X1001X\nmem[42] = 100\nmask = 00000000000000000000000000000000X0XX\nmem[26] = 1")
(def program2 (parse-input raw-input2))

(defn all-x-vals [cnt]
  (apply combo/cartesian-product (repeat cnt [\0 \1])))

(defn apply-mask2 [mask val-bits]
  (map (fn [m v]
         (case m
           \X \X
           \0 v
           \1 \1))
       mask
       val-bits))

(defn gen-address [mask x-vals]
  (if (empty? x-vals)
    mask
    (if (= (first mask) \X)
      (cons (first x-vals) (gen-address (rest mask) (rest x-vals)))
      (cons (first mask) (gen-address (rest mask) x-vals)))))

(defn gen-addresses [mask mem-addr]
  (let [mem-addr-bits (num->bit-seq mem-addr)
        mem-addr-mask (apply-mask2 mask mem-addr-bits)
        x-cnt (count (filter #(= \X %) mem-addr-mask))]
    (->> (map gen-address (repeat mem-addr-mask) (all-x-vals x-cnt))
         (map bit-seq->num))))

(defn exec2 [state [op val :as _instruction]]
  (if (= "mask" op)
    (assoc state :mask (seq val))
    (let [[_ mem-addr] (re-find #"mem\[(.*)\]" op)
          mem-addrs (gen-addresses (:mask state) (Long/parseLong mem-addr))
          mem-map (reduce (fn [res addr] (assoc res addr (Long/parseLong val))) {} mem-addrs)]
      (update state :mem merge mem-map))))

(defn part2 [program]
  (->> (reduce exec2
               {:mask (repeat 36 0)
                :mem  {}}
               program)
       :mem
       (vals)
       (apply +)))
;;2900994392308
