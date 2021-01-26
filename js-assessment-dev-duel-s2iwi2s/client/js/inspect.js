/* eslint-disable no-undef */
$('form').submit(() => {
  const username = $('form input').val()
  console.log(`examining ${username}`)
  clear()
  // Fetch data for given user
  // (https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API)
  fetch(`${USER_URL}?username=${username}`)
    .then(response => response.json()) // Returns parsed json data from response body as promise
    .then(data => {
      console.log(`Got data for ${username}, data=`, data)
      if (data.message) {
        if (data.message) {
          return Promise.reject(data.message)
        }
      } else {
        setData(data)
        $('.user-error').addClass('hide')
        $('.user-results').removeClass('hide') // Display '.user-results' element
      }
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
    errMsg = `<li>${errMsg}</li>`

  } else {
    errMsg = err
  }

  $('.user-error').removeClass('hide')
  $('.user-error').find('.error').html('<h4>ERROR:</h4><ul>' + errMsg + '</ul>')
}

const clear = () => {
  setData({})
  $('.user-results').addClass('hide')
  $('.user-error').removeClass('hide')
  $('.user-error').find('.error').html('<h3>Loading...</h3>')
}

const setData = (data) => {
  $('.username').html(data.username)
  $('.full-name').html(data.name)
  $('.location').html(data.location)
  $('.email').html(data.email ? data.email : '')
  $('.bio').html(data.bio)
  $('.avatar').attr('src', data['avatar-url'])
  $('.titles').html(data.titles ? data.titles.join(', ') : '')
  $('.favorite-language').html(data['favorite-language'] ? data['favorite-language'] : '')
  $('.total-stars').html(data['total-stars'])
  $('.most-starred').html(data['highest-starred'])
  $('.public-repos').html(data['public-repos'])
  $('.perfect-repos').html(data['perfect-repos'] ? data['perfect-repos'] : '')
  $('.followers').html(data.followers)
  $('.following').html(data.following)
}