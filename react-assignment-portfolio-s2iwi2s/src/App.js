
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import styled from 'styled-components';

import Header from './components/Header';
import AboutMe from './components/AboutMe';
import MyStory from './components/MyStory';
import Resume from './components/Resume';
import Portfolio from './components/Portfolio';

const MainContent = styled.div`
  display: flex;
  justify-content: center;
  width: 1140px;
  margin: 0 auto;
  padding: 20px 10px;
  min-height: 80vh;
`
export default function App() {
  return (
    <>
      <Router>
        <Header />
        <MainContent>
          <Switch>
            <Route path="/aboutme" component={AboutMe} />
            <Route path="/mystory" component={MyStory} />
            <Route path="/resume" component={Resume} />
            <Route path="/portfolio" component={Portfolio} />
            <Route path="/" component={AboutMe} />
          </Switch>
        </MainContent>
      </Router>
    </>
  )
}


