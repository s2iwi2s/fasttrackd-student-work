import React from 'react';
import { useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { Main, MainSection, MainHr, MainSpan } from './common/Style';


const MainLinkContainer = styled.div`
 display: flex;
 flex-direction: column;
 justify-content: space-between;
 text-align: center;
 margin: 12px 0;
 width: 30%;
`

const Button = styled.button`
 color: #111111;
 background: transparent;
 border: 2px solid #111111;
 border-radius: 4px;
 transition-duration: 0.2s;
 text-transform: uppercase;
 display: flex;
    justify-content: center;
    width: 100%;
    min-width: 50px;
    text-align: center;
    padding: 14px 28px;
    
 &:hover {
  cursor: pointer;
  background-color: #111111;
  color: white;
 }
`

const Home = () => {
  const history = useHistory();
  return (
    <Main>
      <MainSpan>dev-duel</MainSpan>
      <MainHr />
      <MainSection>
        <MainLinkContainer>
          <span>Judge someone's competence</span>
          <Button onClick={() => history.push('/inspect')}>inspect</Button>
        </MainLinkContainer>
        <MainLinkContainer>
          <span>Ultimate test of developer egos</span>

          <Button onClick={() => history.push('/duel')}>duel</Button>
        </MainLinkContainer>
      </MainSection>
    </Main>
  )
}

export default Home