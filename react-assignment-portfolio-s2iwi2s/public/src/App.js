import logo from './logo.svg';
import './App.css';
import { Route, Switch } from 'react-router-dom';

import Header from './components/Header';
import AboutMe from './components/AboutMe';
import MyStory from './components/MyStory';
import Resume from './components/Resume';
import Portfolio from './components/Portfolio';

export default function App() {
  return (
    <div className="App">
      <Route path="/" component={Header} />
      <Switch>
        <Route path="/" exact={true} component={AboutMe} />
        <Route path="/aboutme" component={AboutMe} />
        <Route path="/mystory" component={MyStory} />
        <Route path="/resume" component={Resume} />
        <Route path="/portfolio" component={Portfolio} />
      </Switch>
    </div>
  )
}


