import React from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import {
    setUsername,
    setRole,
    setTeam,
    setCompany
} from '../slices/userSlice';
import { 
    setAllTeamNames,
    setAllTeamContent
} from '../slices/teamsSlice';

import {
    setCompanyNames,
    setCompaniesContent,
    setSelectedCompany
} from '../slices/companySlice';


import { sendUserAuthRequest, getCompanyRequest, getCompanyTeamsRequest, getAllCompaniesRequest } from '../services/';
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'

const useStyles = makeStyles({
    root: {
        display: 'flex',
        height:'100vh',
        justifyContent:'center',
        alignItems:'center'
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center'
    }
})

const Login = () => {
    const history = useHistory();
    const classes = useStyles();
    const dispatch = useDispatch();

    const setUserState = (user) => {
        dispatch(setUsername(user.username));
        dispatch(setRole(user.role));

        if(user.role.toLowerCase() === 'internal') {
            dispatch(setTeam(user.team));
            dispatch(setCompany(user.company));
        }
        else {
            dispatch(setTeam('Administrator'));
            dispatch(setCompany('CookSystems'));
        }

    }

    const setTeamsState = (teams) => {
        dispatch(setAllTeamNames(teams.teamNames))
        dispatch(setAllTeamContent(teams.teamsContent))
    }

    const setCompanyState = (companies) => {
            if (companies.length === 1) {
                dispatch(setCompanyNames(companies.name))
                dispatch(setCompaniesContent(companies.text))
                dispatch(setSelectedCompany({name: companies.name, content: companies.text}))
            }
            console.log(companies)
            dispatch(setCompanyNames(companies.companyNames))
            dispatch(setCompaniesContent(companies.companiesContent))
        
    }

    const authorizeUser = async (e) => {
        e.preventDefault();
        let user = await sendUserAuthRequest(e.target[0].value, e.target[2].value)
        console.log(user)
        setUserState(user)
        
        if(user.role.toLowerCase() === 'internal') {
            let userCompany = await getCompanyRequest(user.company.name)
            setCompanyState(userCompany)
            setTeamsState(await getCompanyTeamsRequest(user.company.name))
            history.push("/");
        }

        if (user.role.toLowerCase() === 'admin') {
            setCompanyState(await getAllCompaniesRequest())
            history.push("company-select")
        }
    }
    
    
    return (
        <div className={classes.root}>
            <form onSubmit={authorizeUser} className={classes.form}>
                <h2>Log in</h2>
                <TextField id="outlined-basic" label="username" variant="outlined" />
                <TextField id="outlined-basic" label="password" variant="outlined" />
                <Button type="submit" variant="contained">Submit</Button>
            </form>
        </div>
    )
}

export default Login