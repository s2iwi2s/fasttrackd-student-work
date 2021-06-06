import React from 'react';
import { useDispatch } from 'react-redux';
import { 
    setSelectedTeam
} from '../slices/teamsSlice';
import { 
    setSelectedProject
} from '../slices/projectSlice';
import { makeStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Button from '@material-ui/core/Button';

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
    },
    toolbar: theme.mixins.toolbar,
    toolbarCenter: {
        display: 'block',
        marginLeft: 'auto',
        marginRight: 'auto',
        width: '40 %',
    }
}));

const SideBar = (props) => {
    const classes = useStyles();
    const dispatch = useDispatch();

    return (
        <Drawer
            className={classes.drawer}
            variant="permanent"
            classes={{
                paper: classes.drawerPaper,
            }}
            anchor="left"
        >
            <Toolbar>
                <Typography variant="h6" noWrap className={props.backNav ? '' : classes.toolbarCenter}>
                    {props.backNav ? <Button onClick={()=>{dispatch(setSelectedTeam(null)); dispatch(setSelectedProject(null))}}>{'<'}</Button> : null}
                    {props.heading}
                </Typography>
            </Toolbar>
            <Divider />
            <List>
                {props.names.map((text) => (
                    <ListItem onClick={props.setSelected} button key={text}>
                        <ListItemText primary={text} />
                    </ListItem>
                ))}
            </List>
            <Divider />
        </Drawer>
    );
}

export default SideBar;