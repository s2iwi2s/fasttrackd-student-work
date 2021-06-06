import React from 'react';
import { makeStyles } from '@material-ui/core/styles'
import { Button, TextField } from '@material-ui/core';
import { useSelector, useDispatch } from 'react-redux';
import {
    setUsername,
    selectUser
} from '../slices/userSlice';
import { sendEditUserProfileRequest } from '../services/';
import TopBar from '../components/TopBar';

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

const EditProfile = () => {
    const classes = useStyles();
    const dispatch = useDispatch();
    const user = useSelector(selectUser);

    const editUserProfile = async (e) => {
        e.preventDefault();
        setUsernameState(await sendEditUserProfileRequest(e.target[0].value, e.target[2].value));
    }

    const setUsernameState = (user) => {
        dispatch(setUsername(user.username));
    }

    return (
        <div className={classes.root}>
            <TopBar headingName={`Edit ${user.username}'s Profile`} companyName={user.company.name} />
            <form onSubmit={editUserProfile} className={classes.form}>
                <h2>Edit Profile</h2>
                <TextField id="outlined-basic" label="username" variant="outlined" />
                <TextField id="outlined-basic" label="password" variant="outlined" />
                <Button type="submit" variant="contained">Submit</Button>
            </form>
        </div>
    );
}

export default EditProfile;
