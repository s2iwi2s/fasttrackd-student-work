export function calculateTotal(items, tax) {
  let total = 0
  items.forEach(({ price, taxable }) => {
    total += taxable ? (price * Math.abs(tax)) + price : price
  });
  return total
}
