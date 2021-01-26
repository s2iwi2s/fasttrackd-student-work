import Joi from 'joi'

export default {
  query: {
    username1: Joi.string().regex(/^[a-zA-Z0-9][a-zA-Z0-9-]*$/).required().min(1).max(39),
    username2: Joi.string().regex(/^[a-zA-Z0-9][a-zA-Z0-9-]*$/).required().min(1).max(39)
  }
}