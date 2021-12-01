(ns advent-of-code.2021.day01)

(def entries [199 200 208 210 200 207 240 269 260 263])

(defn split-nums [nums-str]
  (.split nums-str "\\s+"))

(def input
  (->> "resources/2021/day01.txt"
       slurp
       split-nums
       (map #(Long/parseLong %))))

(comment
  ;; part 1
  (->> input
       (partition 2 1)
       (filter #(apply < %))
       count)
  ;; 1527

  ;; part 2
  (->> input
       (partition 3 1)
       (map #(apply + %))
       (partition 2 1)
       (filter #(apply < %))
       count)
  ;; 1575
  )


