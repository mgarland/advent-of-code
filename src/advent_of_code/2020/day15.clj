(ns advent-of-code.2020.day15)

(def input [2 0 1 9 5 19])
(def input1 [0 3 6])

(defn init [init-nums]
  (let [state (atom {:seen   (reduce (fn [seen [num turn]]
                                       (assoc seen num turn))
                                     {}
                                     (map (fn [num turn] [num (inc turn)])
                                          (butlast init-nums)
                                          (range)))
                     :turn   (count init-nums)
                     :spoken (last init-nums)})]
    (fn []
      (let [spoken (:spoken @state)
            seen-pos ((:seen @state) spoken)
            turn (:turn @state)]
        (if seen-pos
          (swap! state assoc
                 :seen (assoc (:seen @state) spoken turn)
                 :turn (inc turn)
                 :spoken (- turn seen-pos))
          (swap! state assoc
                 :seen (assoc (:seen @state) spoken turn)
                 :turn (inc turn)
                 :spoken 0))
        (:spoken @state)))))

(defn part1 [init-nums]
  (last (repeatedly (- 2020 (count init-nums)) (init init-nums))))
;;1009

(defn part2 [init-nums]
  (last (repeatedly (- 30000000 (count init-nums)) (init init-nums))))
;;62714
