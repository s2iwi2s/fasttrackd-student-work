import React from 'react';
import { makeStyles } from '@material-ui/core/styles'
import { TextField, MenuItem } from '@material-ui/core';
import { useSelector, useDispatch} from 'react-redux';
import { useHistory, Redirect } from 'react-router-dom';

import TopBar from '../components/TopBar';

import {
        selectCompanyNames,
        selectCompaniesContent,
        setSelectedCompany,
} from '../slices/companySlice'

import {
    getCompanyTeamsRequest
} from '../services/'


import {
    setAllTeamNames,
    setAllTeamContent,
} from '../slices/teamsSlice'

import { 
    selectUser
} from "../slices/userSlice"
const useStyles = makeStyles({
    root: {
        display: 'flex',
        height: '100vh',
        justifyContent: 'center',
        alignItems: 'center'
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center'
    }
})


const AdminCompanySelect = () => {
    const classes = useStyles();
    const history = useHistory();
    const dispatch = useDispatch();
    const companyNames = useSelector(selectCompanyNames)
    const companiesContent = useSelector(selectCompaniesContent)
    const user = useSelector(selectUser)
    let companySelection

    const handleChange = async (event) => {
        dispatch(setSelectedCompany({name: event.target.value, content: companiesContent[companyNames.indexOf(event.target.value)]}))
        let teams = await getCompanyTeamsRequest(event.target.value)
        dispatch(setAllTeamNames(teams.teamNames))
        dispatch(setAllTeamContent(teams.teamsContent))
        history.push('/')
    }


    return (user == null) ? <Redirect to='/login'/> :(
        <div className={classes.root} >
            <TopBar headingName={`Change Company...`} companyName='CookSystems' />
            <form className={classes.form}>
                <h2>Select a company: </h2>
                <TextField select label="Select" value={companySelection} onChange={handleChange}>
                {companyNames.map((option) => (
                    <MenuItem key={option} value={option}>
                    {option}
                    </MenuItem>
                ))}
                </TextField>
            </form>
        </div>
    );
}
    

export default AdminCompanySelect;