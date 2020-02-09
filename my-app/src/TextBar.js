import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Input from '@material-ui/core/Input';

const useStyles = makeStyles(theme => ({
    root: {
        '& > *': {
            margin: theme.spacing(1),
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center'
        },
    },
}));

export default function Inputs() {
    const classes = useStyles();

    return (
        <form className={classes.root} noValidate autoComplete="off">
            <Input placeholder="Input query" inputProps={{ 'aria-label': 'description' }} />
        </form>
    );
}
