module Util
( toOrderedPais
) where



toOrderedPais :: [a] -> [(a, a)]
toOrderedPais [] = []
toOrderedPais (x:xs) = (zip (replicate (length xs) x) xs) ++ toOrderedPais xs
