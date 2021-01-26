import React from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

const Nav = styled.nav`
 display: flex;
 justify-content: flex-end;
 margin: 0;
 background: #333333;
`

const NavContent = styled.div`
  max-width: 1140px;
  margin: 0 auto;
`

const NavContentLink = styled(NavLink)`
 padding: 8px 0;
 color: #fff;
 text-decoration: none;
 text-transform: uppercase;
 font-size: 90%;
 border-bottom: 2px solid transparent;
 transition: border-bottom 0.2s;
 &:hover{
  border-bottom: 2px solid #e67e22;
 }
`

const NavContentUl = styled.ul`
float: right;
list-style: none;
margin-top: 55px;
`

const NavContentLi = styled.li`
display: inline-block;
margin-left: 40px;
`

const Avatar = styled.img`
height: 100px;
width: auto;
float: left;
margin-top: 20px;
`

const Header = () => {
 return (<>
  <Nav>
   <NavContent>
    <Avatar src="img/20190128_000209.jpg" alt="Single Pix" />
    <NavContentUl>
     <NavContentLi>
      <NavContentLink to="/aboutme" >About Me</NavContentLink>
     </NavContentLi>
     <NavContentLi><NavContentLink to="/mystory">My Story</NavContentLink></NavContentLi>
     <NavContentLi><NavContentLink to="/resume">Resume</NavContentLink></NavContentLi>
     <NavContentLi><NavContentLink to="/portfolio">Portfolio</NavContentLink></NavContentLi>
    </NavContentUl>
   </NavContent>
  </Nav>
 </>)
}

export default Header