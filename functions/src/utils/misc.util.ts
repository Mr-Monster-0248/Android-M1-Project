// Check whether two arrays contain the same values
export function checkSameArray(array1: any[], array2: any[]) {
  return array1.length === array2.length
    && array1.every((val, index) => val === array2[index]);
}

// Get random value in array
export function getRandomValueInArray(array: any[]) {
  return array[Math.floor(Math.random() * array.length)];
}
