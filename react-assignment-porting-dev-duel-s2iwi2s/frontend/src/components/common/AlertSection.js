import React from 'react';
import styled, { css } from 'styled-components'

const Section = styled.section`
padding: 30px;
${props => props.type === 'Error' && css`
  color: red;
  `}
`
const InnerSection = styled.div`
background-color: #0700000a;
padding: 10px;
border-radius: 20px;
border: 1px solid #0700000a;
box-shadow: 0px 3px 13px 2px rgba(0, 0, 0, 0.8);
`

const Title = styled.div`
border-radius: 5px;
font-weight: bold;
padding: 10px;
${props => props.type === 'Error' && css`
  background-color: red;
  color: white;
  `}
${props => props.type !== 'Error' && css`
  background-color: rgb(232, 244, 253);
  color: rgb(13, 60, 97);
  `}
`

const LoadingMessage = styled.div`
text-align: center;
color: #000000;
padding: 5px 15px;
`

const AlertSection = ({ errors, type }) => {
  return (<>
    {errors.length > 0 &&
      <Section type={type}>
        <InnerSection>
          {(type === 'Error' || type === 'Info') && <>
            <Title type={type}>{type}</Title>
            <ul>
              {errors.map(item => (
                <li>{item}</li>
              ))}
            </ul>
          </>}

          {type === 'Loading' && <>
            {errors.map(item => (
              <LoadingMessage>{item}</LoadingMessage>
            ))}
          </>}
        </InnerSection>

      </Section>
    }

  </>)
}
export default AlertSection