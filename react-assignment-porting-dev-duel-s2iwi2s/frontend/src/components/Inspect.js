import React, { useState } from 'react';
import styled from 'styled-components'

import { USER_URL } from '../env'
import Title from './common/Title';
import UserResults from './common/UserResults';
import AlertSection from './common/AlertSection';
import { MainSection, FormButton, Main } from './common/Style';
import axios from 'axios';

const Forms = styled.form`
background-color: #0700000a;
border: 1px solid #0700000a;
border-radius: 20px;
box-shadow: 0 5px 20px rgba(0, 0, 0, 0.19), 0 1px 520px rgba(0, 0, 0, 0.23);
display: flex;
flex-direction: column;
margin-top: 0em;
width: 94%;
`

const FormInput = styled.input`
margin: 8px auto;
overflow: visible;
text-align: center;
`

const Inspect = () => {
  const [username, setUsername] = useState('')
  const [details, setDetails] = useState()
  const [message, setMessage] = useState([])
  const [messageType, setMessageType] = useState()

  const showDetails = () => {
    showLoadingMessage()
    axios.get('http://localhost:8080/users/company%201')
      .then(({ data }) => console.log('Got data ==>', data))
      .catch(error => console.error('Got error ==>', error))
    // fetch(`${USER_URL}?username=${username}`)
    //   .then(response => response.json()) // Returns parsed json data from response body as promise
    //   .then(data => {
    //     console.log(`Got data for ${username}, data=`, data)
    //     if (data.message) {
    //       if (data.message) {
    //         return Promise.reject(data.message)
    //       }
    //     } else {
    //       setDetails(data)
    //       setMessage([])
    //     }
    //     return Promise.resolve()
    //   })
    //   .catch(err => showMessage(err))
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
      <Title value={'inspect'} />
      <MainSection>
        <Forms>
          <FormInput name="username" type="text" placeholder="username" value={username} onChange={event => setUsername(event.target.value)} />
          <FormButton type="button" onClick={showDetails}>inspect</FormButton>
        </Forms>
      </MainSection>

      <AlertSection errors={message} type={messageType} />
      <UserResults details={details} />
    </Main>
  )
}

export default Inspect