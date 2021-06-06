import React from 'react';
import Button from '@material-ui/core/Button';
import { ButtonGroup } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { useHistory } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'flex-end',
        '& > *': {
            margin: theme.spacing(1),
        },
    },
    overlayEdit: {
        position: "absolute",
        zIndex : 9999,
    },
}));

const ButtonSet = () => {
    const classes = useStyles();
    const history = useHistory();

    const handleClickEdit = (event) => {
        history.push('edit-project')
    }

    return (
        <div className={classes.root}>
            <ButtonGroup className={classes.overlayEdit} aria-label="outlined button group">
                <Button  onClick={handleClickEdit}>Edit</Button>
                <Button  color="secondary">Delete</Button>
            </ButtonGroup>
        </div>
    );
}

export default ButtonSet;
