
import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './components/Home';
import Inspect from './components/Inspect';
import Duel from './components/Duel';
import ErrorComponent from './components/common/ErrorComponent';
import Header from './components/Header';
import styled from 'styled-components';

const PageContainer = styled.main`
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: auto;
  width: 95%;
  max-width: 750px;
 `

function App() {
  return (
    <PageContainer>
      <Router>

        {/* <Header /> */}
        <Route path={'/'} component={Header} />
        <Switch>
          <Route path={'/'} exact component={Home} />
          <Route path={'/home'} exact component={Home} />
          <Route path={'/inspect'} exact component={Inspect} />
          <Route path={'/duel'} exact component={Duel} />
          <Route component={ErrorComponent} />
        </Switch>
      </Router>
    </PageContainer>
  )
}

export default App;
