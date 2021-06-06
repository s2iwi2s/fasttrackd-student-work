import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { 
    setSelectedTeam, 
    selectAllTeamNames,
    selectSelectedTeam,
    selectAllTeamContent
} from '../slices/teamsSlice';
import {
    selectUser
} from '../slices/userSlice';
import {
    setSelectedProject,
    setAllProjectNames,
    setAllProjectContent,
    selectAllProjectNames,
    selectSelectedProject,
    selectAllProjectContent

} from '../slices/projectSlice';
import {
    selectSelectedCompany
} from '../slices/companySlice'
import {getTeamProjectsRequest} from '../services/teamService'
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import TopBar from '../components/TopBar';
import SideBar from '../components/SideBar';
import { Redirect } from 'react-router-dom';
import ButtonSet from '../components/ButtonSet';


const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
    },
    toolbar: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.default,
        padding: theme.spacing(3),
        marginTop: "10px"
    },
    
    header: {
 
        margin: 0,
        marginBottom: "10px",
        width: '85%',
        borderBottom: 'black solid 1px',
        paddingBottom: '10px',
        
        
        
    },

}));

const Company = () => {
    const classes = useStyles();
    const dispatch = useDispatch();
    const allTeamNames = useSelector(selectAllTeamNames);
    const allTeamContent = useSelector(selectAllTeamContent)
    const selectedTeam = useSelector(selectSelectedTeam);
    const company = useSelector(selectSelectedCompany);
    const selectedProject = useSelector(selectSelectedProject);
    const allProjectNames = useSelector(selectAllProjectNames);
    const allProjectContent = useSelector(selectAllProjectContent)
    const user = useSelector(selectUser);



    const setProjectsState = async (teamName, companyName) => {
        let teamProjects = await getTeamProjectsRequest(teamName, companyName)
        dispatch(setAllProjectNames(teamProjects.projectNames))
        dispatch(setAllProjectContent(teamProjects.projectsContent))
    }

    let selectedDisplay = undefined
    let selectedTitle = undefined
    if (selectedProject != null) {
        selectedTitle = selectedProject.name
        selectedDisplay = selectedProject.content
    }
    else if (selectedTeam != null) {
        selectedTitle = selectedTeam.name
        selectedDisplay = selectedTeam.content
    }
    else if (company != null) {
        selectedTitle = company.name
        selectedDisplay = company.content
    }

    return (user == null) ? <Redirect to='/login'/> :
        <div className={classes.root}>
            <CssBaseline />
            <TopBar headingName={company.name} companyName={company.name} />
            {(selectedTeam == null) ?

                <SideBar backNav={false} heading="Teams" names={allTeamNames} setSelected={(e) => {
                    dispatch(setSelectedTeam({name:e.target.innerText, content:allTeamContent[allTeamNames.indexOf(e.target.innerText)]}));
                    setProjectsState(e.target.innerText, company.name)
                }} /> :
                <SideBar backNav={true} heading="Project" names={allProjectNames} setSelected={(e) => dispatch(setSelectedProject({name:e.target.innerText, content:allProjectContent[allProjectNames.indexOf(e.target.innerText)]}))} /> // (e) => dispatch(setSelectedProject(e.target.innerText))

            }

            <main className={classes.content}>
                <div className={classes.toolbar} />
                {(selectedTeam != null && (selectedTeam.name === user.team)) || (user.role.toLowerCase() === 'admin') ? 
                       <ButtonSet /> : null
                }
                <h2 className={classes.header}>{selectedTitle}</h2> 
                <Typography paragraph>
                    {selectedDisplay}
                </Typography>
            </main>
        </div>
}

export default Company;