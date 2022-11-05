package com.example.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 대표 API 만 하나씩 wrapping 한 wrapper 클래스
 */
public class ZookeeperWrapper implements Watcher, AutoCloseable {

    private final static Logger LOG = LoggerFactory.getLogger(ZookeeperWrapper.class);

    private final ZooKeeper zooKeeper;

    public ZookeeperWrapper(String connectString, String parent, int sessionTimeout) throws IOException {
        LOG.info("Initialize ZookeeperWrapper[connectString={}, parent={}, sessionTimeout={}]", connectString, parent, sessionTimeout);
        zooKeeper = new ZooKeeper(connectString + parent, sessionTimeout, this);
    }

    public String create(final String path, byte[] data, List<ACL> acl, CreateMode createMode) throws KeeperException, InterruptedException {
        return zooKeeper.create(path, data, acl, createMode);
    }

    public Stat exists(final String path, Watcher watcher) throws KeeperException, InterruptedException {
        return zooKeeper.exists(path, watcher);
    }

    public byte[] getData(final String path, boolean watch, Stat stat) throws KeeperException, InterruptedException {
        return zooKeeper.getData(path, watch, stat);
    }

    public List<String> getChildren(final String path, boolean watch) throws KeeperException, InterruptedException {
        return zooKeeper.getChildren(path, watch);
    }

    public void setData(final String path, byte[] data, int version) throws KeeperException, InterruptedException {
        zooKeeper.setData(path, data, version);
    }

    public void removeWatcher(final String path, Watcher watcher, WatcherType watcherType) throws KeeperException, InterruptedException {
        zooKeeper.removeWatches(path, watcher, watcherType, true);
    }

    public void delete(final String path, int version) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, version);
    }

    public void deleteAll(final String path) throws KeeperException, InterruptedException {
        ZKUtil.deleteRecursive(zooKeeper, path);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    LOG.info("Success to connect Session");
                    break;
                case Disconnected:
                    LOG.error("Session Disconnected");
                    break;
                case Expired:
                    LOG.error("Session Expired");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void close() throws Exception {
        zooKeeper.close();
    }
}
