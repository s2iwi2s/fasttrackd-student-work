import './App.css';
import { Route, Switch, Redirect } from 'react-router-dom';
import React from 'react'
import Company from './screens/Company';
import Login from './screens/Login';
import Logout from './components/Logout';
import EditProfile from './screens/EditProfile';
import AdminCompanySelect from './screens/AdminCompanySelect';
import EditProject from './screens/EditProject';

function App() {

  return (
    <div className="App">
      <Switch>
        <Route path='/login' render={() => <Login />} />

        <Route path='/logout' render={()=>{
          return <Logout/>
        }}/>

        <Route path='/edit-profile' render={() => {
          //logic to only display if internal or admin user.
          return <EditProfile />
        }} />

        <Route path='/edit-project' render={() => {
          //logic to only display if internal or admin user.
          return <EditProject />
        }} /> 

        <Route path='/company-select' render={() => {
          //logic to only display if internal or admin user.
          return <AdminCompanySelect />
        }} />

        <Route path='/' render={() => {
          //logic to only display if internal or admin user.
          return <Company />
        }}/>
      </Switch>
    </div>
  );
}

export default App;
