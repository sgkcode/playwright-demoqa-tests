FROM jenkins/jenkins:lts-jdk21

USER root

# Install Docker CLI so Jenkins can spawn Docker-based build agents
RUN apt-get update && apt-get install -y --no-install-recommends \
    docker.io \
    && rm -rf /var/lib/apt/lists/*

# GID 999 is the docker group on most Linux hosts (including Docker Desktop / WSL2).
# If the socket is still inaccessible after starting, check:  stat -c %g /var/run/docker.sock
# and set DOCKER_GID to that value when building: --build-arg DOCKER_GID=<gid>
ARG DOCKER_GID=999
RUN (getent group docker && groupmod -g ${DOCKER_GID} docker) \
    || groupadd -g ${DOCKER_GID} docker \
    && usermod -aG docker jenkins

USER jenkins
