import { React, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { Link as RouterLink } from 'react-router-dom';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';

const drawerWidth240 = 240;
const drawerWidth0 = 0;

const useStyles = makeStyles((theme) => ({
    appBarWithSideBar: {
        display: `flex`,
        width: `calc(100% - ${drawerWidth240}px)`,
        marginLeft: drawerWidth240,
    },
    appBarWithoutSideBar: {
        display: `flex`,
        width: `calc(100% - ${drawerWidth0}px)`,
        marginLeft: drawerWidth0,
    },
    navDisplayFlex: {
        display: `flex`,
        justifyContent: `space-between`
    },
    navbarDisplayFlex: {
        display: `flex`,
        justifyContent: `flex-end`
    },
    linkText: {
        textDecoration: `none`,
        textTransform: `uppercase`,
        color: `white`
    },
    heading: {
        width: '100%',
    }
}));

const TopBar = (props) => {
    const classes = useStyles();

    // set navLinks based on user's role.
    let [navLinks] = useState([
        { title: `Edit Profile`, path: `/edit-profile` },
        { title: props.companyName, path: `/company-select` },
        { title: `logout`, path: `/logout` },
    ]);

    return (
        <AppBar position="fixed" className={(props.headingName === props.companyName) ? classes.appBarWithSideBar : classes.appBarWithoutSideBar}>
            <Toolbar>
                <Typography variant="h6" noWrap className={classes.heading}>
                    {props.headingName}
                </Typography>
                <Container className={classes.navbarDisplayFlex}>
                    <List component="nav" aria-labelledby="main navigation" className={classes.navDisplayFlex}>
                        {navLinks.map(({ title, path }) => (
                            <MenuItem component={RouterLink} to={path} key={title} className={classes.linkText}>
                                <ListItem button>
                                    <ListItemText primary={title} />
                                </ListItem>
                            </MenuItem>
                        ))}
                    </List>
                </Container>
            </Toolbar>
        </AppBar>
    );
}

export default TopBar;
