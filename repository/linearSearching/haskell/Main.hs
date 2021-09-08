linearSearch :: (Eq a) => a -> [a] -> Int
linearSearch _ [] = -1
linearSearch a (x:xs)
    | x == a = 0
    | indexOfTailSearch == -1 = -1
    | otherwise = indexOfTailSearch + 1
	where
	    indexOfTailSearch = linearSearch a xs


toInt :: String -> Int
toInt x = read x :: Int

parseInputs :: [String] -> (Int, [Int])
parseInputs inputs = (toInt (head $ tail inputs), map toInt (tail $ tail inputs))

loop :: Int -> (IO()) -> IO()
loop 0 _ = return()
loop n f = do
  f
  loop (n - 1) f

searchNumber = do
  args <- getLine
  let inputs = parseInputs (words args)
  let searchNumber = fst inputs
  let collection = snd inputs
  let index = linearSearch searchNumber collection
  putStrLn (show index :: String)


main = do
  collectionNumbers <- getLine
  loop (toInt collectionNumbers) searchNumber
