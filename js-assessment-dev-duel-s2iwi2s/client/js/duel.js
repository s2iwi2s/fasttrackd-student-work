/* eslint-disable no-undef */
/*
  TODO
  Fetch 2 user's github data and display their profiles side by side
  If there is an error in finding user or both users, display appropriate error
  message stating which user(s) doesn't exist

  It is up to the student to choose how to determine a 'winner'
  and displaying their profile/stats comparison in a way that signifies who won.
 */
$('form').submit(() => {
  const usernameLeft = $('form input[name="username-left"]').val()
  const usernameRight = $('form input[name="username-right"]').val()
  const left = $('.user-results.left')
  const right = $('.user-results.right')
  clear(left, right)

  fetch(`${USERS_URL}?username1=${usernameLeft}&username2=${usernameRight}`)
    .then(response => response.json()) // Returns parsed json data from response body as promise
    .then(data => {
      console.log(`Got data for ${usernameLeft} and ${usernameRight} data=`, data)
      $('.duel-container').removeClass('hide')

      if (data.message) {
        return Promise.reject(data.message)
      }

      setWinner(data, left, right)

      setData(left, data.leftUser)
      setData(right, data.rightUser)

      $('.duel-error').find('.error').html('')
      $('.duel-error').addClass('hide')
      $('.duel-container').removeClass('hide')
      return Promise.resolve()
    })
    .catch(err => showError(err))

  return false // return false to prevent default form submission
})

showError = (err) => {
  let errMsg = '';
  console.log(err)
  if (err.message || typeof err === 'string') {
    errMsg = err.message ? err.message : err
    errMsg = '<li>' + errMsg.split('.').join('<li>')
    errMsg = errMsg.split('and').join('<li>')
  } else {
    errMsg = err
  }

  $('.duel-container').addClass('hide')
  $('.duel-error').removeClass('hide')
  $('.duel-error').find('.error').html('<h4>ERROR</h4><ul>' + errMsg + '</ul>')
}

const setWinner = (data, left, right) => {
  let isLeftWon = data.leftUser.titles.length > data.rightUser.titles.length
  if (data.leftUser.titles.length == data.rightUser.titles.length) {
    isLeftWon = data.leftUser.followers > data.rightUser.followers
  }
  let titles = data.leftUser.titles
  if (!isLeftWon) {
    titles = data.rightUser.titles
  }
  titles.push('Winner')

  $('.winner').removeClass('winner')
  $('.noborder').removeClass('noborder')
  if (isLeftWon) {
    $(left).find('.avatar').addClass('winner')
    $(right).find('.avatar').addClass('noborder')
  } else {
    $(left).find('.avatar').addClass('noborder')
    $(right).find('.avatar').addClass('winner')
  }
}

const clear = (left, right) => {
  $('.winner').removeClass('winner')

  $('.stats .value, .username, .full-name, .location, .email, .bio').html('')

  $('.duel-container').addClass('hide')
  $('.duel-error').removeClass('hide')
  $('.duel-error').find('.error').html('<h3>Loading...</h3>')
  $('.avatar').attr('src', '')
}

const setData = (form, data) => {
  $(form).find('.username').html(data.username ? data.username : '&nbsp')
  $(form).find('.full-name').html(data.name ? data.name : '&nbsp')
  $(form).find('.location').html(data.location ? data.location : '&nbsp')
  $(form).find('.email').html(data.email ? data.email : '&nbsp')
  $(form).find('.bio').html(data.bio ? data.bio : '&nbsp')
  $(form).find('.avatar').attr('src', data['avatar-url'])
  $(form).find('.titles').html(data.titles ? data.titles.join(', ') : '')
  $(form).find('.favorite-language').html(data['favorite-language'] ? data['favorite-language'] : '')
  $(form).find('.total-stars').html(data['total-stars'])
  $(form).find('.most-starred').html(data['highest-starred'])
  $(form).find('.public-repos').html(data['public-repos'])
  $(form).find('.perfect-repos').html(data['perfect-repos'] ? data['perfect-repos'] : '')
  $(form).find('.followers').html(data.followers)
  $(form).find('.following').html(data.following)
}