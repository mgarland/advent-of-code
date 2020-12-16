(ns advent-of-code.2020.day12
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[_ op cnt] (re-find #"^(\S)(\d+)$" line)]
    [(keyword op) (Integer/parseInt cnt)]))

(defn parse-input [input]
  (->> (str/split input #"\n")
       (mapv parse-line)))

(def raw-input (slurp "resources/2020/day12.txt"))
(def input (parse-input raw-input))

(def raw-input1 "F10\nN3\nF7\nR90\nF11")
(def input1 (parse-input raw-input1))

(def pos-deltas {:E [1 0]
                 :S [0 -1]
                 :W [-1 0]
                 :N [0 1]})

(def right-dir [:E :S :W :N :E :S :W :N])

(defn degrees->steps [degrees]
  (/ (mod degrees 360) 90))

(defn facing-delta [[dir degrees] facing]
  (->> (if (= dir :R) right-dir (reverse right-dir))
       (drop-while #(not= facing %))
       (drop (degrees->steps degrees))
       (first)))

(defn delta [[op num :as command] facing]
  (case op
    (:R :L) [[0 0] (facing-delta command facing)]
    :F [(mapv #(* num %) (pos-deltas facing)) facing]
    (:N :S :E :W) [(mapv #(* num %) (pos-deltas op)) facing]))

(defn move [{:keys [pos facing] :as state} command]
  (let [[pos' facing'] (delta command facing)]
    (assoc state :pos (mapv + pos pos')
                 :facing facing')))

(defn part1 [moves]
  (->> (reduce move {:pos [0 0]
                     :facing :E} moves)
       :pos
       (map #(Math/abs %))
       (apply +)))

(defn waypoint-delta [[dir degrees] pos]
  (loop [pos' pos
         step (degrees->steps degrees)]
    (if (zero? step)
      pos'
      (recur (if (= dir :R)
               [(pos' 1) (- (pos' 0))]
               [(- (pos' 1)) (pos' 0)])
             (dec step)))))

(defn delta2 [[op num :as command] waypoint]
  (case op
    (:R :L) [[0 0] (waypoint-delta command waypoint)]
    :F [(mapv #(* num %) waypoint) waypoint]
    (:N :S :E :W) [[0 0] (mapv + (map * (pos-deltas op) [num num]) waypoint)]))

(defn move2 [{:keys [pos waypoint] :as state} command]
  (let [[pos' waypoint'] (delta2 command waypoint)]
    (assoc state :pos (mapv + pos pos')
                 :waypoint waypoint')))

(defn part2 [moves]
  (->> (reduce move2 {:pos [0 0]
                      :waypoint [10 1]} moves)
       :pos
       (map #(Math/abs %))
       (apply +)))


