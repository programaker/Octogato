# Octogato

Exploring Github API with Scala 3! =D

## How to run?

First, you will need a Github Personal Token. Find the instructions to generate one [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token).

Having the token, you can either pass it using the `--token` command argument or using a `GITHUB_TOKEN` environemnt variable.

When running via Docker (as explained below), just provide the environemnt variable via a `.env` file.

### via sbt

```bash
sbt run --help
```

This will show all available commands. To know more about a specific command, run:

```bash
sbt run <command> --help
```

### via Docker

Build the image:

```bash
./build-image
```

And then:

```bash
./octogato --help
```

Or:

```bash
./octogato <command> --help
```

Like explained for sbt.
