import {
  Router
} from 'express'
import axios from 'axios'
import validate from 'express-validation'
import token from '../../token'

import validation from './validation'
import {
  json
} from 'body-parser'

export default () => {
  let router = Router()

  /** GET /health-check - Check service health */
  router.get('/health-check', (req, res) => res.send('OK'))

  // The following is an example request.response using axios and the
  // express res.json() function
  /** GET /api/rate_limit - Get github rate limit for your token */
  router.get('/rate', (req, res) => {
    axios.get(`http://api.github.com/rate_limit`, {
      headers: {
        'Authorization': token
      }
    }).then(({
      data
    }) => res.json(data))
  })

  /** GET /api/user? - Get user */
  router.get('/user/', validate(validation.user), (req, res) => {
    fetchUserDetails(req.query.username)
      .then(userDetails => res.json(userDetails))
      .catch(error => sendError(error, res))

  })

  /** GET /api/users? - Get users */
  router.get('/users/', validate(validation.users), (req, res) => {
    Promise.all([
        fetchUserDetails(req.query.username1),
        fetchUserDetails(req.query.username2)
      ])
      .then(([left, right]) => res.json({
        leftUser: left,
        rightUser: right
      }))
      .catch(error => sendError(error, res))
  })

  const sendError = (error, res) => {
    let msg = {}
    if (typeof error === 'string')
      msg = {
        message: error
      }
    else if (error.response && error.response.data) msg = error.response.data
    else if (error.message) msg = error.error
    else {
      msg = {
        message: JSON.stringify(error)
      }
    }

    res.json(msg)
    console.log('sendError', error)
  }

  const fetchUserDetails = (username) => axios.get(`https://api.github.com/users/${username}`, {
    headers: {
      'Authorization': token
    }
  }).then(({
    data
  }) => mapUserDetails(data))

  const mapUserDetails = (data) => {
    let userDetails = mapUser(data)
    let repos = []
    return getPagedData(data.repos_url, 1, repos)
      .then(data => {
        mapRepoToUser(data, userDetails, repos.length)
        return userDetails
      })
  }

  const getPagedData =
    (url, page = 1, list = {}) =>
    axios.get(`${url}?page=${page}&per_page=100`).then(({
      data,
      headers
    }) => {
      list.push(...data)
      if (headers.link && headers.link.indexOf('rel="next"') !== -1) {
        return getPagedData(url, page + 1, list)
      }
      return list
    })

  const mapUser = (data) => {
    const temp = {
      "username": data.login,
      "name": data.name,
      "location": data.location,
      "bio": data.bio,
      "avatar-url": data.avatar_url,
      "titles": [],
      "favorite-language": "",
      "public-repos": data.public_repos,
      "total-stars": 0,
      "highest-starred": 0,
      "perfect-repos": 0,
      "followers": data.followers,
      "following": data.following
    }
    return temp
  }

  const mapRepoToUser = (data, userDetails, reposLength) => {
    const mappedData = data.reduce(countMapper, {})
    mappedData.public_languages = mappedData.public_languages ? mappedData.public_languages : {}
    userDetails['favorite-language'] = Object.keys(mappedData.public_languages)
      .reduce((prev, current) => (mappedData.public_languages[prev] > mappedData.public_languages[current]) ?
        prev : current, 0)

    userDetails['total-stars'] = mappedData.total_stars
    userDetails['highest-starred'] = mappedData.highest_starred
    userDetails['perfect-repos'] = mappedData.perfect_repos

    if (mappedData.forked / reposLength > 0.5) {
      userDetails.titles.push('Forker')
    }

    if (Object.keys(mappedData.public_languages).length === 1) {
      userDetails.titles.push('One-Trick Pony')
    }

    if (Object.keys(mappedData.public_languages).length > 10) {
      userDetails.titles.push('Jack of all Trades')
    }

    if (userDetails.following / userDetails.followers > 0.5) {
      userDetails.titles.push('Stalker')
    }

    if (userDetails.followers / userDetails.following > 0.5) {
      userDetails.titles.push('Mr. Popular')
    }

    if (Object.keys(mappedData.public_languages).length > 0) {
      userDetails.titles.push('Programmer')
    }
  }

  const countMapper = (m, curr) => {
    m.total_stars = curr.stargazers_count + 1 || 1
    m.highest_starred = m.highest_starred > curr.stargazers_count ? m.highest_starred : curr.stargazers_count || curr.stargazers_count

    if (!m.perfect_repos) m.perfect_repos = 0
    m.perfect_repos = !curr.has_issues ? m.perfect_repos + 1 : m.perfect_repos

    if (!m.forked) m.forked = 0
    m.forked = curr.fork ? m.forked + 1 : m.forked

    if (!curr.private) {
      m.public_repo_count = m.public_repo_count + 1 || 1

      if (!m.public_languages) {
        m.public_languages = {}
      }
      if (curr.language) {
        m.public_languages[curr.language] = m.public_languages[curr.language] + 1 || 1
      }
    }
    return m
  }

  return router
}