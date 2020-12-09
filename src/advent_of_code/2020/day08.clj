(ns advent-of-code.2020.day08
  (:require [clojure.string :as str]
            [clojure.set :as set]))


(defn parse-line [line]
  (let [[op arg] (str/split line #"\s+")]
    [op (Long/parseLong arg)]))

(defn parse-input [input]
  (->> (str/split input #"\n")
       (mapv parse-line)))

(def program
  (->> "resources/2020/day08.txt"
       (slurp)
       (parse-input)))

(def test1 "nop +0\nacc +1\njmp +4\nacc +3\njmp -3\nacc -99\nacc +1\njmp -4\nacc +6")
(def program1 (parse-input test1))

(defn run-program [program]
  (let [pc-end (count program)]
    (loop [pc 0
           acc 0
           visited-pcs {}]
      (cond
        (visited-pcs pc)
        [:infinite-loop acc]

        (<= pc-end pc)
        [:completed acc]

        :else
        (let [[op arg] (get program pc)
              visited-pcs (assoc visited-pcs pc true)]
          (case op
            "nop" (recur (inc pc) acc visited-pcs)
            "acc" (recur (inc pc) (+ acc arg) visited-pcs)
            "jmp" (recur (+ pc arg) acc visited-pcs)))))))

(defn part1 [program]
  (second (run-program program)))

(defn get-nop-jmp-pcs [program]
  (->> (mapv (fn [[instr _arg] pc] (when (#{"nop" "jmp"} instr) pc)) program (range))
       (remove nil?)
       (vec)))

(defn part2 [program]
  (-> (for [nop-jmp-pc (get-nop-jmp-pcs program)
            :let [program' (update program
                                   nop-jmp-pc
                                   (fn [[instr arg]]
                                     (if (= instr "nop")
                                       ["jmp" arg]
                                       ["nop" arg])))
                  result (run-program program')]
            :when (= :completed (first result))]
        (second result))
      (first)))
