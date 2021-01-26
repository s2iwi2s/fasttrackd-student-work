import vorpal from 'vorpal'
import {
  prompt
} from 'inquirer'

import {
  readFile,
  writeFile,
  chooseRandom,
  createPrompt,
  createQuestions,
  showError,
  listDir,
  createFileDir
} from './lib'


const dir = './out/'
const dir_quiz = `quiz/`
const dir_answers = `answers/`

const cli = vorpal()

const askForQuestions = [{
    type: 'input',
    name: 'numQuestions',
    message: 'How many questions do you want in your quiz?',
    validate: input => {
      const pass = input.match(/^[1-9]{1}$|^[1-9]{1}[0-9]{1}$|^100$/)
      return pass ? true : 'Please enter a valid number!'
    }
  },
  {
    type: 'input',
    name: 'numChoices',
    message: 'How many choices should each question have?',
    validate: input => {
      const pass = input.match(/^(?:[2-4]|0[2-4]|4)$/)
      return pass ? true : 'Please enter a valid number!'
    }
  }
]

const readFromFile = (folder, fileName, data) => {
  const currDir = `${dir + folder}`
  return readFile(`${currDir}/${fileName}`, data)
}

const writeToFile = (folder, fileName, data) => {
  const currDir = `${dir + folder}`
  createFileDir(`${dir}`)
  createFileDir(`${currDir}`)
  return writeFile(`${currDir + fileName}`, data)
}

const createQuiz = title =>
  prompt(askForQuestions)
  .then(answer => createPrompt(answer, (i, name, list) => {
    list.push({
      type: 'input',
      name: `${name}-answer`,
      message: `Enter answer to question ${i}`
    })
  }))
  .then(template => prompt(template))
  .then(questions => writeToFile(dir_quiz, title, JSON.stringify(questions)))
  .then(result => console.log(result))
  .catch(showError)

const takeQuiz = (title, output) => {
  readFromFile(dir_quiz, title)
    .then(result => JSON.parse(result))
    .then(parsedData => createQuestions(parsedData))
    .then(questions => takeQuizAndValidate(questions))
    .then(quiz => writeToFile(dir_answers, output, JSON.stringify(quiz), true))
    .catch(showError)
}

const takeQuizAndValidate = (questions) => prompt(questions).then(answers => new Promise((resolve, reject) => {
  let count = 0
  questions.map(item => {
    if (answers[item.name] === item.answer) {
      count++
    }
  })

  console.log(`Number of correct answers: ${count}/${questions.length}`)
  resolve({
    quiz: questions,
    answers: answers
  })
}))

const takeRandomQuiz = (quizzes, output, n) => {
  Promise.all(
      quizzes.map(fileName =>
        readFromFile(dir_quiz, fileName)
        .then(content => JSON.parse(content))
        .then(parsedData => createQuestions(parsedData))
        .then(questions => chooseRandom(questions))
      )
    )
    .then(r => r.reduce((r, a) => r.concat(a), []))
    .then(q =>
      q.map((item, i) => {
        item.name = `question-${i + 1}`
        return item
      })
    )
    // .then(questions => prompt(questions))
    .then(questions => takeQuizAndValidate(questions))
    .then(answers => JSON.stringify(answers))
    .then(stringData => writeToFile(dir_answers, output, stringData))
    .then(msg => console.log(msg))
    .catch(showError)
}

cli
  .command(
    'create <fileName>',
    'Creates a new quiz and saves it to the given fileName'
  )
  .action(function (input, callback) {
    return createQuiz(input.fileName)
  })

cli
  .command(
    'take <fileName> <outputFile>',
    'Loads a quiz and saves the users answers to the given outputFile'
  )
  .autocomplete({
    data: function (input, callback) {
      listDir(`${dir}${dir_quiz}`).then(callback)
    }
  })
  .action(function (input, callback) {
    takeQuiz(input.fileName, input.outputFile)
  })

cli
  .command(
    'random <outputFile> <fileNames...>',
    'Loads a quiz or' +
    ' multiple quizes and selects a random number of questions from each quiz.' +
    ' Then, saves the users answers to the given outputFile'
  )
  .action(function (input, callback) {
    takeRandomQuiz(input.fileNames, input.outputFile)
  })

cli
  .command(
    'grade <fileName>',
    'grade the answers to the given fileName'
  ).autocomplete({
    data: function (input, callback) {
      listDir(`${dir}${dir_answers}`).then(callback)
    }
  })
  .action(function (input, callback) {
    gradeQuiz(input.fileName)
  })

const gradeQuiz = (fileName) => {
  readFromFile(dir_answers, fileName)
    .then(results => JSON.parse(results))
    .then(parsedResults => {
      let count = 0
      parsedResults.quiz.map(item => {
        let msg = item.name
        if (parsedResults.answers[item.name] === item.answer) {
          msg = `${item.name}: /`
          count++
        } else {
          msg = `${item.name}: X. The correct answer is ${parsedResults.answers[item.name]}`
        }
        console.log(msg)
      })
      console.log(`Number of correct answers: ${count} out of ${parsedResults.quiz.length}`)
    })
}

cli.delimiter(cli.chalk['yellow']('quizler>')).show()