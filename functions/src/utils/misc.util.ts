// Get random value in array
export function getRandomValueInArray(array: any[]) {
  return array[Math.floor(Math.random() * array.length)];
}

// Check whether two arrays contain the same values
export function checkArraysEqual<T>(array1: T[], array2: T[]) {
  return array1.length === array2.length
    && array1.every(item => array2.indexOf(item) > -1);
}
