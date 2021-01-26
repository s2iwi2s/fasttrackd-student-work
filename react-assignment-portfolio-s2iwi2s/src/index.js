import React from 'react';
import ReactDOM from 'react-dom';
import { createGlobalStyle } from 'styled-components';

import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

const GlobalStyle = createGlobalStyle`
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body {
  background-color: #000000;
  background-image: linear-gradient(rgba(0, 0, 0, 0.9), rgba(0, 0, 0, 0.7)), url(img/bg2.jpg);
  background-size: cover;
  background-position: center;
  color: rgb(189, 189, 189);
  font-family: 'Lato', 'Arial', sans-serif;
  font-weight: 300;
  font-size: 20px;
  text-rendering: optimizeLegibility;
  overflow-x: hidden;
  height: 100vh;
  background-attachment: fixed;
  line-height: 145%;
}

p{
  display: block;
  margin-block-start: 0.5em;
  margin-block-end: 0.5em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
}

.title {
  font-size: 300%;
  font-weight: bold;
  padding-bottom: 20px;
  text-decoration: underline;
}

.sub-content {
  width: 100%
}

.text-content {
  line-height: 145%;
  text-align: justify; 
  width: 100%;
}

.pt-40 {
  padding-top: 40px;
}

.pt-70 {
  padding-top: 70px;
}

nav .active {
  font-weight: bold;
}

span.hightlight {
  font-weight: bold;
  font-size: 120%;
  margin-right: 5px;
}

.border {
  border: 1px solid blanchedalmond;
}

.w100{
  width: 100%
}

.react-pdf__Document {
  display: flex;
  flex-direction: column;
  align-items: center;
}
`

ReactDOM.render(
  <React.StrictMode>
    <GlobalStyle />
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
