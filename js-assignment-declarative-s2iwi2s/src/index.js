import defaultObject from './hof-exercise';

const { calculateTotalDeclarative, prices, taxSum, baseSum } = defaultObject;

const items = [{
 price: 100,
 taxable: false
}, {
 price: 50,
 taxable: false
}, {
 price: 250,
 taxable: true
}]
const tax = 0.0975

const pricesAry = prices(items)
const bSum = baseSum(items, tax)
const txSum = taxSum(items, tax)
const totalDeclarative = calculateTotalDeclarative(items, tax)

console.log(`items=`, items)
console.log(`prices=${pricesAry}`)
console.log(`baseSum=${bSum}`)
console.log(`taxSum=${txSum}`)
console.log(`totalDeclarative=${totalDeclarative}`)