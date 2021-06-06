import React from 'react';
import { makeStyles } from '@material-ui/core/styles'
import { Button, TextField } from '@material-ui/core';
import { useSelector, useDispatch} from 'react-redux';
import { useHistory, Redirect } from 'react-router-dom';


import TopBar from '../components/TopBar';

import {
        selectSelectedCompany,
        setSelectedCompany,
        setCompanyNames,
        setCompaniesContent
} from '../slices/companySlice'


import {
    selectSelectedTeam,
    setSelectedTeam,
    setAllTeamNames,
    setAllTeamContent
} from '../slices/teamsSlice'

import {
    selectSelectedProject,
    setSelectedProject,
    setAllProjectNames,
    setAllProjectContent
} from '../slices/projectSlice'

import {
    selectUser
} from '../slices/userSlice'

import {
    patchCompanyNameRequest,
    patchCompanyContentRequest,
    getCompanyTeamsRequest,
    getAllCompaniesRequest,
    patchTeamNameRequest,
    patchTeamContentRequest,
    getTeamProjectsRequest,
    patchProjectNameRequest,
    patchProjectContentRequest
    
} from '../services/'




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
        justifyContent: 'center',
        width: "75%"
    },
    formButton:  {
        width: "10%",
        margin: "auto",
        marginTop: "20px"
    }
})


const EditProject = () => {
    const classes = useStyles();
    const history = useHistory();
    const dispatch = useDispatch();
    const company = useSelector(selectSelectedCompany)
    const team = useSelector(selectSelectedTeam)
    const project = useSelector(selectSelectedProject)
    const user = useSelector(selectUser)
    
    let editSelection = undefined

    if (project != null) {
        editSelection = project
    }
    else if (team != null) {
        editSelection = team
    }
    else if (company != null) {
        editSelection = company
    }

  const handleSubmit = async (e) => {
        e.preventDefault();
        let newName = e.target[0].value
        let newText = e.target[1].value
        

        if (project != null) {
            await patchProjectNameRequest(company.name, team.name, project.name, newName)
            await patchProjectContentRequest(company.name, team.name, newName, newText)
            dispatch(setSelectedProject({name: newName, content: newText}))
        }
        else if (team != null) {
            await patchTeamNameRequest(company.name, team.name, newName)
            await patchTeamContentRequest(company.name, newName, newText)
            dispatch(setSelectedTeam({name: newName, content: newText}))
            let projects = await getTeamProjectsRequest(newName, company.name)
            if (projects !== null) {
                dispatch(setAllProjectNames(projects.projectNames))
                dispatch(setAllProjectContent(projects.projectsContent))
            }

            let teams = await getCompanyTeamsRequest(company.name)
            if (teams !== null) {
                dispatch(setAllTeamNames(teams.teamNames))
                dispatch(setAllTeamContent(teams.teamsContent))

            }
            
            

  
        }
        else if (company != null) {
            await patchCompanyNameRequest(company.name, newName)
            await patchCompanyContentRequest(newName, newText)
            dispatch(setSelectedCompany({name: newName, content: newText}))
            let teams = await getCompanyTeamsRequest(newName)
            if (teams !== null) {
                dispatch(setAllTeamNames(teams.teamNames))
                dispatch(setAllTeamContent(teams.teamsContent))

            }
            
            if (user.role.toLowerCase() === 'admin') {
                let companies = await getAllCompaniesRequest()
                dispatch(setCompanyNames(companies.companyNames))
                dispatch(setCompaniesContent(companies.companiesContent))
            }
        }

        history.push('/')

    }
 
        return (company == null) ? <Redirect to='/login'/> : (
            <div className={classes.root} >
                <TopBar headingName={`Edit Project...`} companyName={company.name} />
                <form className={classes.form} onSubmit={handleSubmit}>
                    <section>
                        <h2>Header: </h2>
                        <TextField id="standard-basic" fullWidth defaultValue={editSelection.name} />
                    </section> 
                    
                    <section>
                        <h2>Content: </h2>
                        <TextField id="outlined-multiline-static"  fullWidth multiline rows={10} defaultValue={editSelection.content}variant="outlined"/>
                    </section> 
                    <Button className = {classes.formButton} type="submit" variant="contained">Submit</Button>
                </form>
            </div>
        );
}
  


export default EditProject;