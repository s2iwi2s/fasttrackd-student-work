import React, { useState } from 'react';
import styled from 'styled-components'

import { USERS_URL } from '../env'
import AlertSection from './common/AlertSection';
import Title from './common/Title';
import UserResults from './common/UserResults';
import { Main, FormButton } from './common/Style';

const FormContainer = styled.section`
display: flex;
justify-content: space-around;
flex-direction: column;
align-items: center;
margin: 10px 0;
`

const DuelContainer = styled.section`
 align-items: flex-start;
 border: 1px solid #0700000a;
 display: flex;
 flex-direction: row;
 height: 100%;
 justify-content: space-around;
 margin: 10px 0;
`

const Forms = styled.form`
background-color: #0700000a;
border: 1px solid #0700000a;
border-radius: 20px;
box-shadow: 0 5px 20px rgba(0, 0, 0, 0.19), 0 1px 520px rgba(0, 0, 0, 0.23);
margin-top: 0em;
display: flex;
flex-direction: column;
width: 94%;
`

const InputContainer = styled.div`
display: flex;
flex-wrap: wrap;
margin: 8px auto
`

const FormInput = styled.input`
margin: 4px 4px;
overflow: visible;
text-align: center;
`


const Duel = () => {
  const [username1, setUsername1] = useState('')
  const [username2, setUsername2] = useState('')
  const [details, setDetails] = useState()
  const [message, setMessage] = useState([])
  const [messageType, setMessageType] = useState()

  const showDetails = () => {
    showLoadingMessage()

    fetch(`${USERS_URL}?username1=${username1}&username2=${username2}`)
      .then(response => response.json()) // Returns parsed json data from response body as promise
      .then(data => {
        console.log(`Got data for ${username1} and ${username2} data=`, data)

        if (data.message) {
          return Promise.reject(data.message)
        }
        setMessage([])
        setWinner(data)
        setDetails(data)

        return Promise.resolve()
      })
      .catch(err => showMessage(err))
  }

  const setWinner = (data) => {
    let isLeftWon = data.leftUser.titles.length > data.rightUser.titles.length
    if (data.leftUser.titles.length === data.rightUser.titles.length) {
      isLeftWon = data.leftUser.followers > data.rightUser.followers
    }

    let titles = data.leftUser.titles
    if (!isLeftWon) {
      titles = data.rightUser.titles
    }
    titles.push('Winner')

    data.leftUser.winner = isLeftWon
    data.rightUser.winner = !isLeftWon
  }

  const showLoadingMessage = () => {
    setDetails(null)
    setMessage(['Loading...'])
    setMessageType('Loading')
  }

  const showMessage = (msg) => {
    setDetails(null)
    let msgList = [];
    console.log(msg)
    if (msg.message || typeof msg === 'string') {
      if (msg.message) {
        msgList.push(JSON.stringify(msg.message))
      } else {
        msgList = msg.split('and')
      }
    } else {
      msgList.push(JSON.stringify(msg))
    }
    if (!msgList) {
      msgList = []
    }

    setMessage(msgList)
    setMessageType('Error')
  }

  return (
    <Main>
      <Title value={'duel'} />
      <FormContainer>
        <Forms>
          <InputContainer>
            <FormInput name="username1" type="text" placeholder="Username 1" value={username1} onChange={event => setUsername1(event.target.value)} />
            <FormInput name="username2" type="text" placeholder="Username 2" value={username2} onChange={event => setUsername2(event.target.value)} />
          </InputContainer>
          <FormButton type="button" onClick={showDetails}>duel</FormButton>
        </Forms>
      </FormContainer>

      <AlertSection errors={message} type={messageType} />
      {details && <DuelContainer>
        <UserResults details={details.leftUser} />
        <UserResults details={details.rightUser} />
      </DuelContainer>}

    </Main>
  )
}

export default Duel