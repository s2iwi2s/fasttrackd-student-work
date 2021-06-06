import React from 'react';
import {useDispatch} from 'react-redux';
import {Redirect} from 'react-router-dom';
import {
    setUser
} from '../slices/userSlice';

const Logout = () => {
    const dispatch = useDispatch();
    dispatch(setUser({
        username:null,
        role:null,
        team:null,
        company:null
      }));
    return(
        <Redirect to='/login'/>
    );
}

export default Logout;