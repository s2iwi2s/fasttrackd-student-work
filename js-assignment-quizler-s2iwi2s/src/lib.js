import fs from 'fs'

// chooseRandom: (arr: [], numItems: number) -> []
export const chooseRandom = (array = [], numItems) => {
  const retArr = []
  if (!array || array.length <= 1) {
    return array
  }
  let numItemsTemp = !numItems || array.length < numItems ? randomNumber(array.length) + 1 : numItems

  const indexes = []
  for (let i = 0; i < numItemsTemp;) {
    const idx = randomNumber(array.length)
    if (indexes.indexOf(idx) === -1) {
      retArr.push(array[idx])
      indexes.push(idx)
      i++
    }
  }
  return retArr
}

const randomNumber = max => {
  return Math.floor(Math.random() * max)
}

// createPrompt: (input: { numQuestions: number, numChoices: number }) -> [{type: string, name: string, message: string}]
export const createPrompt = (input = {
  numQuestions: 1,
  numChoices: 2
}, callback) => {
  let list = []
  if (!input) {
    return list
  }
  let {
    numQuestions = 1, numChoices = 2
  } = input
  for (let i = 1; i <= numQuestions; i++) {
    const name = `question-${i}`
    list.push({
      type: 'input',
      name: name,
      message: `Enter question ${i}`
    })
    for (let j = 1; j <= numChoices; j++) {
      list.push({
        type: 'input',
        name: `${name}-choice-${j}`,
        message: `Enter answer choice ${j} for question ${i}`
      })
    }
    if (callback) {
      callback(i, name, list)
    }
  }
  return list
}

let createQuestionsCounter = 1

// createQuestions: (params: [{string:string}]) -> [{type: string, name: string, message: string, choices: [string]}]
export const createQuestions = (input = {}) => {
  let list = []
  let question
  for (const value in input) {
    if (value.indexOf('-choice-') !== -1) {
      question.choices.push(input[value])
    } else if (value.indexOf('-answer') !== -1) {
      question.answer = input[value]
    } else {
      question = {
        type: 'list',
        name: value,
        message: input[value],
        choices: []
      }
      list.push(question)
    }
  }

  return list
}

export const listDir = (name) =>
  new Promise((resolve, reject) => {
    fs.readdir(name, (err, files) => err ? reject(err) :
      resolve(files)
    )
  })

export const readFile = path =>
  new Promise((resolve, reject) => {
    fs.readFile(path, (err, data) => (err ? reject(err) : resolve(data)))
  })

export const writeFile = (path, data) =>
  new Promise((resolve, reject) => {
    fs.writeFile(path, data, err =>
      err ? reject(err) : resolve('File saved successfully')
    )
  })

export const createFileDir = (dir) => {
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir)
  }
}
export const showError = err => console.error(err)