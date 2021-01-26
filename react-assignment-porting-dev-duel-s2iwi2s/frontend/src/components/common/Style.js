import styled from 'styled-components';

const none = styled.span`
`

export const Main = styled.div`
display: flex;
flex-direction: column;
height: 90%;

border-radius: 4px;
background-color: #ffffff;
box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
`

export const MainSection = styled.section`
display: flex;
justify-content: space-around;
flex-direction: row;
align-items: center;
margin: 10px 0;
`

export const MainSpan = styled.span`
  display: flex;
  justify-content: center;
  margin: 24px 0;
  height: 60px;
  font-size: 56px;
  color: #111;
  font-family: 'Helvetica Neue', sans-serif;
  letter-spacing: 2px;
  line-height: 1;
`
export const MainHr = styled.hr`
 margin: 0 24px;
 border-color: #555555;
 background-color: #555555;
 color: #555555;
`
export const Button = styled.button`
 &:hover {
  background: red
  border: 
 }
`
export const FormButton = styled.button`
background: transparent;
border: 2px solid #111111
border-radius: 4px;
margin: 8px auto;
overflow: visible;
text-transform: uppercase;
width: 120px;

&: hover {
 background-color: red;
 color: white;
 cursor: pointer;
 transition: 0.3s;
}
`

export default none